package com.example.favoritapp.ado;

import com.example.favoritapp.modelos.Usuario;

import junit.framework.TestCase;

public class UsuarioADOTest extends TestCase {

    public void testValidarUsuario() {
        UsuarioADO us = new UsuarioADO(null);

        Usuario modelo = new Usuario();
        modelo.setEmail("usuario@gmail.com");
        modelo.setClave("prueba");

        boolean resultado = us.validarUsuario(modelo);

        assertEquals(true, resultado);

    }
}