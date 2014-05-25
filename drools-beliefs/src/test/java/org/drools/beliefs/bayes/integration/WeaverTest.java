package org.drools.beliefs.bayes.integration;

import org.drools.beliefs.bayes.JunctionTree;
import org.drools.beliefs.bayes.assembler.BayesPackage;
import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.definitions.impl.ResourceTypePackage;
import org.junit.Test;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.conf.RuleEngineOption;
import org.kie.internal.io.ResourceFactory;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class WeaverTest {

    @Test
    public void testBayesPackageWeaving() throws Exception {
        KnowledgeBuilderImpl kbuilder = new KnowledgeBuilderImpl();
        kbuilder.add( ResourceFactory.newClassPathResource("Sprinkler.xmlbif", AssemblerTest.class), ResourceType.BAYES );


        KnowledgeBase kbase = getKnowledgeBase();
        kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

        InternalKnowledgePackage kpkg = (InternalKnowledgePackage) kbase.getKiePackage("bayes");
        Map<ResourceType, ResourceTypePackage> map = kpkg.getResourceTypePackages();
        BayesPackage existing  = (BayesPackage) map.get( ResourceType.BAYES );
        JunctionTree jtree =  existing.getJunctionTree("Sprinkler");
        assertNotNull( jtree );
    }

    protected KnowledgeBase getKnowledgeBase() {
        KieBaseConfiguration kBaseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kBaseConfig.setOption(RuleEngineOption.PHREAK);
        return getKnowledgeBase(kBaseConfig);
    }

    protected KnowledgeBase getKnowledgeBase(KieBaseConfiguration kBaseConfig) {
        kBaseConfig.setOption(RuleEngineOption.PHREAK);
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kBaseConfig);
        return kbase;
    }



}
