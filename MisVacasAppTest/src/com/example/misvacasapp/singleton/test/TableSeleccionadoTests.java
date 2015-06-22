package com.example.misvacasapp.singleton.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.singleton.TableSeleccionado;

public class TableSeleccionadoTests extends TestCase {

	private TableSeleccionado table;

	@Before
	public void setUp() throws Exception {
		table = new TableSeleccionado();
	}

	@Test
	public void testTableSeleccionado() {
		table = new TableSeleccionado();
		assertTrue(table.getTable().isEmpty());
	}

	@Test
	public void testGetTable() {
		table.getTable().put(1, true);
		table.getTable().put(2, false);
		table.getTable().put(3, true);
		assertTrue(table.getTable().get(1));
		assertFalse(table.getTable().get(2));
		assertTrue(table.getTable().get(3));
	}
}
