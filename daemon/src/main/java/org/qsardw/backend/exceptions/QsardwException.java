/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.exceptions;

import org.openscience.cdk.exception.CDKException;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class QsardwException extends Exception {

    /**
     * Constructs an instance of <code>ChemLibException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public QsardwException(String msg) {
        super(msg);
    }

    public QsardwException(CDKException exception) {
        super(exception.getMessage());
    }

    public QsardwException(Exception exception) {
        super(exception.getMessage());
    }

    public QsardwException(OpsinResult result) {
        super(result.getMessage());
    }
}
