package utils.uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import utils.org.semanticweb.skos.SKOSLiteralVisitor;
import utils.org.semanticweb.skos.SKOSTypedLiteral;
import utils.org.semanticweb.skos.SKOSUntypedLiteral;

/**
 * Author: Simon Jupp<br>
 * Date: Aug 26, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSUntypedLiteralImpl implements SKOSUntypedLiteral {

    OWLLiteral constant;
    String lang;
    String literal;

    public SKOSUntypedLiteralImpl(OWLDataFactory factory, String literal, String lang) {
        this.lang = lang;
        this.literal = literal;
        if (lang == null) {
            this.constant = factory.getOWLStringLiteral(literal);
        }
        else {
            this.constant = factory.getOWLStringLiteral(literal, lang);
        }
    }

    public String getLiteral() {
        return literal;
    }

    public boolean isTyped() {
        return false;
    }

    public SKOSUntypedLiteral getAsSKOSUntypedLiteral() {
        return this;
    }

    public SKOSTypedLiteral getAsSKOSTypedLiteral() {
        throw new OWLRuntimeException("This is not a SKOS Typed Constant");
    }

    public void accept(SKOSLiteralVisitor visitor) {
        visitor.visit(this);
    }

    public OWLLiteral getAsOWLConstant() {
        return constant;
    }

    public String getLang() {
        return lang;
    }

    public boolean hasLang() {
        if (lang != null) {
            return true;
        }
        return false;
    }

    public boolean hasLang(String lang) {
        if (this.lang.equals(lang)) {
            return true;
        }
        return false;
    }
}
