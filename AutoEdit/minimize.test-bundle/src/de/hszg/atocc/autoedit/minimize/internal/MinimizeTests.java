package de.hszg.atocc.autoedit.minimize.internal;

import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlUtilsException;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.test.AbstractTestHelper;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;

public final class MinimizeTests extends AbstractTestHelper {

    @Test
    public void testMinimizeDea() throws XmlUtilsException, SerializationException {
        final Document originalDeaDocument = getXmlService().documentFromFile("DeaToMinimize.xml");

        final Document expectedDeaDocument = getXmlService().documentFromFile("MinimizedDea.xml");
        final Automaton expectedDea = getAutomatonService().automatonFrom(expectedDeaDocument);

        final Document resultDocument = getWebUtils().post(
                "http://localhost:8081/autoedit/minimize", originalDeaDocument);
        final Document minimizedDeaDocument = getXmlService().getContent(resultDocument);
        final Automaton minimizedDea = getAutomatonService().automatonFrom(minimizedDeaDocument);

        Assert.assertEquals(expectedDea, minimizedDea);
    }

}
