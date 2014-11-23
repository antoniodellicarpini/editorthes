package utils.uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.OWLAxiom;

import utils.org.semanticweb.skos.SKOSAssertion;
import utils.org.semanticweb.skos.SKOSAssertionVisitor;

/**
 * Author: Simon Jupp<br>
 * Date: Aug 26, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public abstract class SKOSAssertionImpl implements SKOSAssertion {

    OWLAxiom ax;


    public SKOSAssertionImpl(OWLAxiom assAx) {
        this.ax = assAx;
    }

    public OWLAxiom getAxiom() {
        return ax;
    }

    public void accept(SKOSAssertionVisitor visitor) {
    }
}
