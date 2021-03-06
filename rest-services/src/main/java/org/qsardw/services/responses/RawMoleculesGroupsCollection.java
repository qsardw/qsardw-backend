/*
 * The MIT License
 *
 * Copyright 2014 Javier Caride Ulloa <javier.caride@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.qsardw.services.responses;

import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import org.qsardw.datamodel.beans.DatasetRawMoleculeGroup;
/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
@XmlRootElement
public class RawMoleculesGroupsCollection {
    private int totalGroups;
    private Collection<DatasetRawMoleculeGroup> groups;

    public int getTotalGroups() {
        return totalGroups;
    }

    public void setTotalGroups(int totalGroups) {
        this.totalGroups = totalGroups;
    }

    public Collection<DatasetRawMoleculeGroup> getGroups() {
        return groups;
    }

    public void setGroups(Collection<DatasetRawMoleculeGroup> groups) {
        this.groups = groups;
    }
}
