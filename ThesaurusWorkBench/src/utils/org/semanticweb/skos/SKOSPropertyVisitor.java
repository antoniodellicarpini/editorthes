package utils.org.semanticweb.skos;

import utils.org.semanticweb.skos.properties.*;

/**
 * Author: Simon Jupp<br>
 * Date: Sep 5, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public interface SKOSPropertyVisitor {

    void visit (SKOSAltLabelProperty property);

    void visit (SKOSBroaderProperty property);

    void visit (SKOSBroaderTransitiveProperty property);

    void visit (SKOSBroadMatchProperty property);

    void visit (SKOSChangeNoteDataProperty property);

    void visit (SKOSChangeNoteObjectProperty property);

    void visit (SKOSCloseMatchProperty property);

    void visit (SKOSDefinitionDataProperty property);

    void visit (SKOSDefinitionObjectProperty property);

    void visit (SKOSEditorialNoteDataProperty property);

    void visit (SKOSEditorialNoteObjectProperty property);

    void visit (SKOSExactMatchProperty property);

    void visit (SKOSExampleDataProperty property);

    void visit (SKOSExampleObjectProperty property);

    void visit (SKOSHasTopConceptProperty property);

    void visit (SKOSHiddenLabelProperty property);

    void visit (SKOSHistoryNoteDataProperty property);

    void visit (SKOSHistoryNoteObjectProperty property);

    void visit (SKOSInSchemeProperty property);

    void visit (SKOSMappingRelationProperty property);

    void visit (SKOSMemberProperty property);

    void visit (SKOSMemberListProperty property);

    void visit (SKOSNarrowMatchProperty property);

    void visit (SKOSNarrowerProperty property);

    void visit (SKOSNarrowerTransitiveProperty property);

    void visit (SKOSNotationProperty property);

    void visit (SKOSNoteDataProperty property);

    void visit (SKOSNoteObjectProperty property);

    void visit (SKOSPrefLabelProperty property);

    void visit (SKOSRelatedProperty property);

    void visit (SKOSRelatedMatchProperty property);

    void visit (SKOSScopeNoteDataProperty property);

    void visit (SKOSScopeNoteObjectProperty property);

    void visit (SKOSSemanticRelationProperty property);

    void visit (SKOSTopConceptOfProperty property);

    void visit (SKOSObjectProperty property);

    void visit (SKOSDataProperty property);

    void visit (SKOSAnnotationProperty property);
}
