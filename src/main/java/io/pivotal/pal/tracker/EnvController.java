package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    String port;
    String memoryLimit;
    String cfInstanceIndex;
    String cfInstanceAddr;

    public EnvController(@Value("${PORT:NOT SET}") String port,@Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit, @Value("${CF_INSTANCE_INDEX:NOT SET}")String CFInstanceIndex,@Value("${CF_INSTANCE_ADDR:NOT SET}") String CFInstanceAddr){

        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = CFInstanceIndex;
        this.cfInstanceAddr = CFInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {

        Map<String,String> envProperties = new HashMap<String,String>();
        envProperties.put("PORT",port);
        envProperties.put("MEMORY_LIMIT",memoryLimit);
        envProperties.put("CF_INSTANCE_INDEX",cfInstanceIndex);
        envProperties.put("CF_INSTANCE_ADDR",cfInstanceAddr);

        return envProperties;
    }
}
