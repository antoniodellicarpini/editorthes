package utils.uk.ac.manchester.cs.skos.properties;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;

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
import utils.org.semanticweb.skos.SKOSPropertyVisitor;
import utils.org.semanticweb.skos.properties.SKOSNarrowerTransitiveProperty;
import utils.uk.ac.manchester.cs.skos.SKOSObjectPropertyImpl;
import utils.uk.ac.manchester.cs.skos.SKOSRDFVocabulary;

/**
 * Author: Simon Jupp<br>
 * Date: Sep 4, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSNarrowerTransitivePropertyImpl extends SKOSObjectPropertyImpl implements SKOSNarrowerTransitiveProperty {
    public SKOSNarrowerTransitivePropertyImpl(OWLDataFactory factory) {
        super(factory.getOWLObjectProperty(IRI.create(SKOSRDFVocabulary.NARROWERTRANS.getURI())));
    }

    public void accept(SKOSPropertyVisitor visitor) {
        visitor.visit(this);
    }
}
