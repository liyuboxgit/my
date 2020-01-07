package liyu.test.springboot.init.service;

import liyu.test.springboot.init.entity.Resources;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class ResourcesService {
    public List<Resources> loadMenu(String username) {
        Resources[] res = new Resources[]{new Resources("Amodule")};
        List<Resources> list = Arrays.asList(res);
        return list;
    }
}
