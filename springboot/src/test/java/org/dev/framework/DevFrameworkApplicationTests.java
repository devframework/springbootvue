package org.dev.framework;

import org.dev.framework.exception.CustomException;
import org.dev.framework.modules.sys.util.SequenceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DevFrameworkApplicationTests {

    @Autowired
    SequenceUtil sequenceUtil;

    @Test
    public void contextLoads() {

    }

    @Test
    public void wflowPNG() {


    }

    @Test
    public void FlowCode() {
        try {
            sequenceUtil.GeneratorCode("FLOW_CODE");
        } catch (CustomException ee) {
            ee.printStackTrace();
        }
    }

}
