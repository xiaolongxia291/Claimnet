package tracy.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tracy.client.entity.Claim;
import tracy.client.entity.Text;
import tracy.client.service.ClaimService;
import tracy.client.service.TextService;

import java.util.List;

@Api(tags = "检索模块")
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private TextService textService;
    @Autowired
    private ClaimService claimService;

    @ApiOperation(value = "输入关键词查询text文档", response = List.class)
    @GetMapping("/text")
    public List<Text> text(@ApiParam(value = "关键词", required = true)String keyword,
                           @ApiParam(value = "当前页", required = true)Integer curPage,
                           @ApiParam(value = "页大小", required = true)Integer pageSize) {
        if(curPage<=0)curPage=1;
        if(pageSize<=0)pageSize=10;
        return textService.text(keyword,curPage,pageSize);
    }

    @ApiOperation(value = "输入id查询claim文档", response = Claim.class)
    @GetMapping("/claim")
    public Claim claim(@ApiParam(value = "id", required = true)String id) {
        return claimService.claim(id);
    }

}
