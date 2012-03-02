package de.hszg.atocc.compile.internal;

import de.hszg.atocc.core.pluginregistry.AbstractServiceComponent;
import de.hszg.atocc.core.pluginregistry.WebService;

@WebService(url = "/compile", resource = Compile.class)
public class CompileComponent extends AbstractServiceComponent {

}
