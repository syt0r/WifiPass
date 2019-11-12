package ua.sytor.wifipass;

import org.junit.Assert;
import org.junit.Test;
import ua.sytor.wifipass.interactor.command_executer.CommandExecutorContract;
import ua.sytor.wifipass.interactor.command_executer.CommandExecutor;

public class ApplicationTest {

    CommandExecutorContract commandExecutor = new CommandExecutor();

    @Test
    public void testCmdCommand() {

        String output = null;
        try {
            output = commandExecutor.execCommand("su -c cat /data/misc/wifi/wpa_supplicant.conf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("output " + output);
        Assert.assertEquals(1, 1);

    }

    @Test
    public void testCommand() {

    }

    @Test
    public void testSu() {

    }

    @Test
    public void getContent() {

    }

}