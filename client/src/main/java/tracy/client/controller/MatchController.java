package tracy.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tracy.client.service.TextService;
import tracy.client.util.PairResult;

@Api(tags = "匹配模块")
@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private TextService textService;

    @ApiOperation(value = "输入两个text id进行匹配", response = PairResult.class)
    @GetMapping("/pair")
    public PairResult pair(@ApiParam(value = "text id1", required = true)String id1,
                           @ApiParam(value = "text id2", required = true)String id2){
        return textService.pair(id1,id2);
    }
}
