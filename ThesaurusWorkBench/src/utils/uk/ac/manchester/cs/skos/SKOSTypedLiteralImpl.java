package utils.uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import utils.org.semanticweb.skos.SKOSDataType;
import utils.org.semanticweb.skos.SKOSLiteralVisitor;
import utils.org.semanticweb.skos.SKOSTypedLiteral;
import utils.org.semanticweb.skos.SKOSUntypedLiteral;

/**
 * Author: Simon Jupp<br>
 * Date: Aug 27, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSTypedLiteralImpl implements SKOSTypedLiteral {

    public String literal;
    public SKOSDataTypeImpl type;
    OWLLiteral constant;

    public SKOSTypedLiteralImpl(OWLDataFactory factory, SKOSDataType dataType, String literal) {
        this.literal = literal;
        this.type = (SKOSDataTypeImpl) dataType;
        constant = factory.getOWLTypedLiteral(literal, type.getAsOWLDataType());
    }

    public String getLiteral() {
        return literal;
    }

    public boolean isTyped() {
        return true;
    }

    public SKOSUntypedLiteral getAsSKOSUntypedLiteral() {
        throw new OWLRuntimeException("Not a SKOS Untyped Constant");
    }

    public SKOSTypedLiteral getAsSKOSTypedLiteral() {
        return this;
    }

    public void accept(SKOSLiteralVisitor visitor) {
        visitor.visit(this);
    }

    public SKOSDataType getDataType() {
        return type;
    }

    public OWLLiteral getAsOWLConstant() {
        return constant;  //To change body of created methods use File | Settings | File Templates.
    }
}
