/**
 * (c) Midland Software Limited 2018
 * Name     : TokBoxRestService.java
 * Author   : ferraciolliw
 * Date     : 06 Apr 2018
 */
package com.wiltech.tokBox.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class TokBoxRestService {

    @GetMapping("/cool-cars")
    @CrossOrigin(origins = "http://localhost:4200")
    public String sessionId() {
        TokBoxSessionAppServer tokBoxSessionAppServer = new TokBoxSessionAppServer();
        return tokBoxSessionAppServer.toString();

        //return tokBoxSessionAppServer.test();
    }

}
