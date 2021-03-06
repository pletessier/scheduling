/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2015 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ActiveEon Team
 *                        http://www.activeeon.com/
 *  Contributor(s):
 *
 * ################################################################
 * $$ACTIVEEON_INITIAL_DEV$$
 */
package org.ow2.proactive.scheduler.common.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.proactive.annotation.PublicAPI;


/**
 * RegexpMatcher should help you when looking for patterns in a string.
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 0.9.1
 */
@PublicAPI
public class RegexpMatcher {

    private Pattern pattern = null;
    private Matcher matcher = null;
    private ArrayList<String> matches;
    private int cursor;

    /**
     * Create a new instance of RegexpMatcher.
     *
     * @param pattern the pattern to find in the string. It is no need to surround your string with the .* characters.
     */
    public RegexpMatcher(String pattern) {
        this.pattern = Pattern.compile("(" + pattern + ")");
    }

    /**
     * Matches the regular expression with the given string.
     *
     * @param toMatch the String where to find the regular expression.
     */
    public void matches(String toMatch) {
        matcher = pattern.matcher(toMatch);
        matches = new ArrayList<String>();
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        cursor = 0;
    }

    /**
     * Once matches, returns the next string instance matches by the pattern.
     *
     * @return the next string instance matches by the pattern.
     */
    public String next() {
        return matches.get(cursor++);
    }

    /**
     * Tell if an other matches is existing.
     *
     * @return true if there is an other matches, false otherwise.
     */
    public boolean hasNext() {
        return matches.size() > cursor;
    }

    /**
     * Return an array that contains all the string matched by the given pattern in the given string.
     *
     * @param pattern the pattern to find in the string. It is no need to surround your string with the .* characters.
     * @param toMatch the String where to find the regular expression.
     * @return an array that contains all the string matched by the given pattern in the given string.
     * 			return an empty array if nothing found.
     */
    public static String[] matches(String pattern, String toMatch) {
        RegexpMatcher matcher = new RegexpMatcher(pattern);
        matcher.matches(toMatch);
        return matcher.matches.toArray(new String[] {});
    }

}
