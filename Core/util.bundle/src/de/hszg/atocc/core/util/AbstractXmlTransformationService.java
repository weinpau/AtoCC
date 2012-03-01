package de.hszg.atocc.core.util;

import org.restlet.resource.Post;
import org.w3c.dom.Document;

public abstract class AbstractXmlTransformationService extends AbstractRestfulWebService {

    private Document input;
    private Document output;
    private Document result;
    
    private XmlUtilService xmlUtils;
    private XmlValidatorService xmlValidator;

    @Post
    public final Document process(Document document) {
        tryToGetRequiredServices();
        
        input = document;
        
        try {
            transform();
            
            result = xmlUtils.createResult(output);
        } catch (XmlTransormationException e) {
            result = xmlUtils.createResultWithError(e.getMessage(), e.getCause(), getLocale());
        }
        
        return result;
    }

    protected abstract void transform() throws XmlTransormationException;

    protected final Document getInput() {
        return input;
    }
    
    protected final void setOutput(Document document) {
        output = document;
    }
    
    protected final void validateInput(String schemaID) throws XmlValidationException {
        xmlValidator.validate(input, schemaID);
    }
    
    protected final void validateOutput(String schemaID) throws XmlValidationException {
        xmlValidator.validate(output, schemaID);
    }
    
    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
        xmlValidator = getService(XmlValidatorService.class);
    }
}
