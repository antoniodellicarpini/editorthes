package utils.org.semanticweb.skos;

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
 * Date: Apr 21, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * <br>
 * You can have multiple concept schemes in a <code>SKOSDataset</code>. Each concpet scheme can contain
 * concepts. This class provides some conenience methods for getting access to those concepts.
 * <br>
 * SKOS reference has a notion of importing concept schemes but the spec is not satisfactory at the moment to provide any concrete implementatiuon.
 */
public interface SKOSConceptScheme extends SKOSEntity {

    Set<SKOSConcept> getTopConcepts(SKOSDataset dataset);

    Set<SKOSConcept> getConceptsInScheme (SKOSDataset dataset);

//    Set<SKOSConceptScheme> getImportedConceptSchemes (SKOSDataset dataset);
}
