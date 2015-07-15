
package org.qsardw.backend.exceptions;

import org.openscience.cdk.exception.CDKException;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

/**
 *
 * @author javier
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
