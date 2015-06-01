package org.jmxtrans.agent;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
public class StatsDOutputWriterIntegrationTest {

    @Test
    public void test() throws IOException {
        StatsDOutputWriter writerSpy = spy(new StatsDOutputWriter());
        Map<String, String> settings = new HashMap<String, String>();
        settings.put(StatsDOutputWriter.SETTING_HOST, "localhost");
        settings.put(StatsDOutputWriter.SETTING_PORT, "8125");
        writerSpy.postConstruct(settings);

        doReturn(true).when(writerSpy).doSend(anyString());

        for (int measureIndex = 0; measureIndex < 10; measureIndex++) {
            for (int metricIndex = 0; metricIndex < 5; metricIndex++) {
                writerSpy.writeQueryResult("jmxtrans-agent-test-metric-" + metricIndex,
                        "counter", Integer.valueOf(10 * measureIndex + metricIndex));
            }
        }

        //verify that method was called 50 times on a mock
        verify(writerSpy, times(50)).doSend(anyString());
    }

    @Test
    public void verifyNonNumberValuesAreIgnored() throws IOException {
        StatsDOutputWriter writerSpy = spy(new StatsDOutputWriter());
        Map<String, String> settings = new HashMap<String, String>();
        settings.put(StatsDOutputWriter.SETTING_HOST, "localhost");
        settings.put(StatsDOutputWriter.SETTING_PORT, "8125");
        writerSpy.postConstruct(settings);

        doReturn(true).when(writerSpy).doSend(anyString());

        for (int metricIndex = 0; metricIndex < 5; metricIndex++) {
            writerSpy.writeQueryResult("jmxtrans-agent-test-metric-" + metricIndex,
                    "counter", String.valueOf(10 * metricIndex));
        }

        //verify that method was never called on a mock
        verify(writerSpy, times(0)).doSend(anyString());
    }
}
