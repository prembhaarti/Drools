package com.flipkart.drools;

import java.math.BigDecimal;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;

import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.flipkart.drools.ItemCity.City;
import com.flipkart.drools.ItemCity.Type;

/* This is a sample class to launch a rule. */

public class DroolsTest {
    public static final void main(String[] args) {
        try {

            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

            ItemCity item1 = new ItemCity();
            item1.setPurchaseCity(City.PUNE);
            item1.setTypeofItem(Type.MEDICINES);
            item1.setSellPrice(new BigDecimal(10));
            ksession.insert(item1);

            ItemCity item2 = new ItemCity();
            item2.setPurchaseCity(City.PUNE);
            item2.setTypeofItem(Type.GROCERIES);
            item2.setSellPrice(new BigDecimal(10));
            ksession.insert(item2);

            ItemCity item3 = new ItemCity();
            item3.setPurchaseCity(City.NAGPUR);
            item3.setTypeofItem(Type.MEDICINES);
            item3.setSellPrice(new BigDecimal(10));
            ksession.insert(item3);

            ItemCity item4 = new ItemCity();
            item4.setPurchaseCity(City.NAGPUR);
            item4.setTypeofItem(Type.GROCERIES);
            item4.setSellPrice(new BigDecimal(10));
            ksession.insert(item4);

            ksession.fireAllRules();

            System.out.println(item1.getPurchaseCity().toString() + " "
                    + item1.getLocalTax().intValue());

            System.out.println(item2.getPurchaseCity().toString() + " "
                    + item2.getLocalTax().intValue());

            System.out.println(item3.getPurchaseCity().toString() + " "
                    + item3.getLocalTax().intValue());

            System.out.println(item4.getPurchaseCity().toString() + " "
                    + item4.getLocalTax().intValue());
            ksession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        kbuilder.add(ResourceFactory.newClassPathResource("pune.drl"), ResourceType.DRL);
        kbuilder.add(ResourceFactory.newClassPathResource("nagpur.drl"), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kbuilder.getErrors();

        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        return kbase;
    }
}
