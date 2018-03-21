package org.cloudfoundry.samples.music.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cloudfoundry.samples.music.domain.ApplicationInfo;
import org.cloudfoundry.samples.music.domain.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InfoController {
    @Autowired(required = false)
    private Cloud cloud;

    private Environment springEnvironment;

    private static Color color;

    @Autowired
    public InfoController(@Value("${demo.color:blue}")String color, Environment springEnvironment) {
        this.springEnvironment = springEnvironment;
        this.color = new Color(color);
    }

    @RequestMapping(value = "/appinfo")
    public ApplicationInfo info(HttpServletRequest request) throws Exception {

        Map<String, Object> vcap = new HashMap<String, Object>();

        if (System.getenv("PORT") == null) {
            vcap.put("containerAddr", "localhost");
        } else {
            vcap.put("containerAddr", request.getLocalAddr() + ":"
                    + request.getLocalPort());
        }

        String instanceIndex = getVcapApplicationMap().getOrDefault(
                "instance_index", "no index environment variable").toString();
        vcap.put("instanceIndex", instanceIndex);

        String instanceAddr = System.getenv("CF_INSTANCE_ADDR");
        if (instanceAddr == null) {
            instanceAddr = "localhost";
        }
        vcap.put("instanceAddr", instanceAddr);


        return new ApplicationInfo(springEnvironment.getActiveProfiles(), getServiceNames(), color, vcap);
    }

    @RequestMapping(value = "/service")
    public List<ServiceInfo> showServiceInfo() {
        if (cloud != null) {
            return cloud.getServiceInfos();
        } else {
            return new ArrayList<>();
        }
    }

    private String[] getServiceNames() {
        if (cloud != null) {
            final List<ServiceInfo> serviceInfos = cloud.getServiceInfos();

            List<String> names = new ArrayList<>();
            for (ServiceInfo serviceInfo : serviceInfos) {
                names.add(serviceInfo.getId());
            }
            return names.toArray(new String[names.size()]);
        } else {
            return new String[]{};
        }
    }

    @SuppressWarnings("rawtypes")
    private Map getVcapApplicationMap() throws Exception {
        return getEnvMap("VCAP_APPLICATION");
    }

    private Map getEnvMap(String vcap) throws Exception {
        String vcapEnv = System.getenv(vcap);
        ObjectMapper mapper = new ObjectMapper();

        if (vcapEnv != null) {
            Map<String, ?> vcapMap = mapper.readValue(vcapEnv, Map.class);
            return vcapMap;
        }

        return new HashMap<String, String>();
    }

}