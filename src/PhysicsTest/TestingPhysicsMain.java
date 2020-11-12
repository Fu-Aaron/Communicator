package PhysicsTest;

import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;

public class TestingPhysicsMain {
    public static void main(String[] args) {
        TestbedModel model = new TestbedModel();
        TestList.populateModel(model);
        model.addCategory("My Tests");
        model.addTest(new MyTest1());
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));
        TestbedPanel panel = new TestPanelJ2D(model);
        JFrame testbed = new TestbedFrame(model, panel);
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
