package me.dabpessoa.test;

import me.dabpessoa.test.service.HistorialService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by diego.pessoa on 24/07/2017.
 */

public class DataBaseConnectionTest {

    @Test
    public void deveSeConectarComOPostgres() {

    }

    @Test
    public void deveSomarDoisValores() {
        HistorialService hs = new HistorialService();

        Assert.assertEquals("1 + 2 deve ser 3", 3, hs.soma(1, 2));
        Assert.assertEquals("5 + 2 deve ser 7", 7, hs.soma(5, 2));
        Assert.assertEquals("236 + 512 deve ser 748", 748, hs.soma(236, 512));

    }

}
