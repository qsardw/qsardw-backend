/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package org.qsardw.services;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class QsardwRest extends ResourceConfig {
    public QsardwRest() {
        register(org.qsardw.services.filters.CrossDomainFilter.class);
    }
}
