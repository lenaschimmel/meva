
package org.itemscript.core.connectors;

import org.itemscript.core.values.PutResponse;

/**
 * A {@link PutCallback} that does nothing when the request succeeds and throws a RuntimeException when it fails.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class IgnoreResultPutCallback implements PutCallback {
    @Override
    public void onError(Throwable e) {
        throw new RuntimeException(e);
    }

    @Override
    public void onSuccess(PutResponse putResponse) {}
}
