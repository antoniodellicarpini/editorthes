package utils.org.semanticweb.skos;

import utils.org.semanticweb.skos.extensions.SKOSLabelRelation;

import java.net.URI;
import java.util.Set;

/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Simon Jupp<br>
 * Date: Mar 25, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * <br>
 * A SKOS entity represent the main types of SKOS objects avaialable, from the SKOS reference we currently
 * have Concept, ConceptScheme, LabelRelation, Collection, Ordered Collection as SKOS enitites. 
 */
public interface SKOSEntity extends SKOSObject {

    boolean isSKOSConcept();

    SKOSConcept asSKOSConcept();

    boolean isSKOSConceptScheme();

    SKOSConceptScheme asSKOSConceptScheme();

    boolean isSKOSLabelRelation();

    SKOSLabelRelation asSKOSLabelRelation();

    boolean isSKOSResource();

    SKOSResource asSKOSResource();
    /**
     * @return The URI for the SKOS entity 
     */
    URI getURI();

    Set<SKOSAnnotation> getSKOSAnnotations(SKOSDataset dataset);

    void accept(SKOSEntityVisitor visitor);

}
