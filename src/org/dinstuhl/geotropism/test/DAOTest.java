package org.dinstuhl.geotropism.test;

import junit.framework.TestCase;

import java.util.ArrayList;

import org.dinstuhl.geotropism.*;
import org.dinstuhl.geotropism.dao.*;
import org.dinstuhl.geotropism.domain.*;


public class DAOTest extends TestCase {

	public void testGetMessages() {
		
		String id = new String("-89.36512765");
		ArrayList<GeoMessage> messages;
		messages = MessageDAO.getMessages(id);
		
		fail("Not yet implemented");
	}

}
