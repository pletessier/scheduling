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
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s): ActiveEon Team - http://www.activeeon.com
 *
 * ################################################################
 * $$ACTIVEEON_CONTRIBUTOR$$
 */
package org.ow2.proactive.resourcemanager.selection.policies;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.objectweb.proactive.core.node.NodeException;
import org.ow2.proactive.jmx.naming.JMXTransportProtocol;
import org.ow2.proactive.resourcemanager.authentication.Client;
import org.ow2.proactive.resourcemanager.common.NodeState;
import org.ow2.proactive.resourcemanager.common.event.RMEventType;
import org.ow2.proactive.resourcemanager.common.event.RMNodeEvent;
import org.ow2.proactive.resourcemanager.nodesource.NodeSource;
import org.ow2.proactive.resourcemanager.rmnode.RMNode;
import org.ow2.proactive.scripting.Script;
import org.ow2.proactive.scripting.ScriptResult;
import org.ow2.proactive.scripting.SelectionScript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.security.Permission;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * 
 * Test for NodeSourcePriorityPolicy
 *
 */
public class TestNodeSourcePolicy {

    class Node implements RMNode {

        String name;
        String nsName;

        Node(String name, String nsName) {
            this.name = name;
            this.nsName = nsName;
        }

        public int compareTo(RMNode o) {
            return name.compareTo(o.getNodeName());
        }

        public ScriptResult<Boolean> executeScript(SelectionScript script) {
            return null;
        }

        public HashMap<SelectionScript, Integer> getScriptStatus() {
            return null;
        }

        public void clean() throws NodeException {
        }

        @Override
        public String getNodeInfo() {
            return null;
        }

        public String getNodeName() {
            return name;
        }

        public org.objectweb.proactive.core.node.Node getNode() {
            return null;
        }

        public String getVNodeName() {
            return null;
        }

        public String getHostName() {
            return null;
        }

        public String getDescriptorVMName() {
            return null;
        }

        public String getNodeSourceName() {
            return nsName;
        }

        public String getNodeURL() {
            return null;
        }

        public NodeSource getNodeSource() {
            return null;
        }

        public NodeState getState() {
            return null;
        }

        public long getStateChangeTime() {
            return 0;
        }

        public Client getProvider() {
            return null;
        }

        public Client getOwner() {
            return null;
        }

        public Permission getUserPermission() {
            return null;
        }

        public Permission getAdminPermission() {
            return null;
        }

        public RMNodeEvent getAddEvent() {
            return null;
        }

        public RMNodeEvent getLastEvent() {
            return null;
        }

        public boolean isFree() {
            return false;
        }

        public boolean isDown() {
            return false;
        }

        public boolean isToRemove() {
            return false;
        }

        public boolean isBusy() {
            return false;
        }

        public boolean isConfiguring() {
            return false;
        }

        public boolean isLocked() {
            return false;
        }

        public void setFree() {
        }

        public void setBusy(Client owner) {
        }

        public void setToRemove() {
        }

        public void setDown() {
        }

        public void setConfiguring(Client owner) {
        }

        public void lock(Client owner) {
        }

        public void setNodeSource(NodeSource nodeSource) {
        }

        public void setAddEvent(RMNodeEvent addEvent) {
        }

        public void setLastEvent(RMNodeEvent lastEvent) {
        }

        public void setJMXUrl(JMXTransportProtocol protocol, String address) {
        }

        public String getJMXUrl(JMXTransportProtocol protocol) {
            return null;
        }

        @Override
        public <T> ScriptResult<T> executeScript(Script<T> script, Map<String, Serializable> bindings) {
            return null;
        }

        @Override
        public boolean isProtectedByToken() {
            return false;
        }

        @Override
        public RMNodeEvent createNodeEvent(RMEventType eventType, NodeState previousNodeState,
                String initiator) {
            return null;
        }

        @Override
        public RMNodeEvent createNodeEvent() {
            return null;
        }

    }

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void action() throws Exception {

        /**
         * Creating a config with ns names 0, 1, 2, 3, ... 9
         */
        File config = tmpFolder.newFile("policies");
        BufferedWriter out = new BufferedWriter(new FileWriter(config));
        for (int i = 0; i < 10; i++) {
            out.write(i + "\n");
        }
        out.close();

        System.setProperty(NodeSourcePriorityPolicy.CONFIG_NAME_PROPERTY, config.getAbsolutePath());
        NodeSourcePriorityPolicy policy = new NodeSourcePriorityPolicy();

        // checking the arrangement of all nodes
        List<RMNode> nodes = createNodes();
        List<RMNode> res = policy.arrangeNodes(1000, nodes, null);

        assertEquals("Incorrect result size", 400, res.size());
        Iterator<RMNode> iterator = res.iterator();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                RMNode node = iterator.next();
                assertEquals("Incorrect arrangenemt (" + i + ", " + j + ")", String.valueOf(i),
                        node.getNodeName());
            }
        }

    }

    private List<RMNode> createNodes() {
        /**
         * Creating an artificial list of nodes
         */
        List<RMNode> nodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                nodes.add(new Node(String.valueOf(j), String.valueOf(j)));
            }
        }
        return nodes;
    }
}
