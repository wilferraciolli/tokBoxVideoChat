/**
 * (c) Midland Software Limited 2018
 * Name     : TokBoxRestService.java
 * Author   : ferraciolliw
 * Date     : 16 Apr 2018
 */
package com.wiltech.tokBox.tokbox;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Tok box rest service.
 */
@RestController
public class TokBoxRestService {

    @GetMapping("/tokbox")
    @CrossOrigin(origins = "http://localhost:4200")
    public String sessionId() {
        TokBoxSessionAppServer tokBoxSessionAppServer = new TokBoxSessionAppServer();
        System.out.println(tokBoxSessionAppServer.test());
        return tokBoxSessionAppServer.toString();

        //return tokBoxSessionAppServer.test();
    }

    @GetMapping("/test")
    @CrossOrigin(origins = "http://localhost:4200")
    public String sessionIds() {
        return "test";

        //return tokBoxSessionAppServer.test();
    }

}
