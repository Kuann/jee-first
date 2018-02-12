package me.kuann.jee.first.it;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

@ArquillianSuiteDeployment
public class Deployments {

	@Deployment
	@OverProtocol("Servlet 3.0")
	@Cleanup(phase = TestExecutionPhase.BEFORE)
	@UsingDataSet(value = { "dataset/course.xml" })
	public static WebArchive createDeployementForAllTest()  {
		return createDeployment();
	}
	
	public static WebArchive createDeploymentForEachTest() {
		return createDeployment();
	}
	
	private static WebArchive createDeployment() {
		PomEquippedResolveStage pomResolver = Maven.resolver().loadPomFromFile("pom.xml");
		return ShrinkWrap.create(WebArchive.class, "jee.war")
				.addPackages(true, "me.kuann")
				.addAsLibraries(pomResolver.importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "classes/META-INF/beans.xml")
				.addAsResource("persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("wildfly-ds.xml");
	}
}
