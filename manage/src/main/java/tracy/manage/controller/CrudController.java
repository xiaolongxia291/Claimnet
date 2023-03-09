package tracy.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tracy.manage.entity.Claim;
import tracy.manage.entity.Text;
import tracy.manage.service.ClaimService;
import tracy.manage.service.TextService;
import tracy.manage.util.CrudResult;
import tracy.manage.util.PageQuery;

import java.util.List;

@Api(tags = "数据管理模块")
@RestController
@RequestMapping("/crud")
public class CrudController {
    @Autowired
    private TextService textService;
    @Autowired
    private ClaimService claimService;

    //增
    @ApiOperation(value = "插入text数据", response = CrudResult.class)
    @PostMapping("/insert/text")
    public CrudResult<Text> insertText(@ApiParam(value = "待插入数据")@RequestBody List<Text> docs){
        if(docs.size()!=0)return textService.insert(docs);
        return new CrudResult<>("输入为空",docs);
    }

    @ApiOperation(value = "插入claim数据", response = CrudResult.class)
    @PostMapping("/insert/claim")
    public CrudResult<Claim> insertClaim(@ApiParam(value = "待插入数据")@RequestBody List<Claim> docs){
        if(docs.size()!=0)return claimService.insert(docs);
        return new CrudResult<>("输入为空",docs);
    }

    //删

    @ApiOperation(value = "清空索引", response = String.class)
    @PostMapping("/delete/clear")
    public String clear(@ApiParam(value = "索引名", required = true)@RequestBody String indexName) {
        if(!indexName.equals("text")&&!indexName.equals("claim"))return "索引不存在";
        if(indexName.equals("claim")) claimService.clear();
        else textService.clear();
        return "";
    }

    @ApiOperation(value = "删除text数据", response = String.class)
    @PostMapping("/delete/text")
    public String deleteText(@ApiParam(value = "待删除id列表")@RequestBody List<String> ids){
        if(ids.size()==0) return "输入为空";
        textService.delete(ids);
        return "";
    }

    @ApiOperation(value = "删除claim数据", response = String.class)
    @PostMapping("/delete/claim")
    public String deleteClaim(@ApiParam(value = "待删除id列表")@RequestBody List<String> ids){
        if(ids.size()==0) return "输入为空";
        claimService.delete(ids);
        return "";
    }

    //查
    @ApiOperation(value = "查询全部text数据", response = List.class)
    @GetMapping("/select/all/text")
    public List<Text> selectAllText(@ApiParam(value = "当前页")int curPage,
                                    @ApiParam(value = "页大小")int pageSize){
        if(curPage<=0)curPage=1;
        if(pageSize<=0)pageSize=10;
        return textService.selectAll(curPage,pageSize);
    }

    @ApiOperation(value = "按id或申请号查询text数据", response = List.class)
    @PostMapping("/select/conditon/text")
    public List<Text> selectTextCondition(@ApiParam(value = "查询条件")@RequestBody PageQuery<Text> pageQuery){
        return textService.selectCondition(pageQuery);
    }

    @ApiOperation(value = "查询全部claim数据", response = List.class)
    @GetMapping("/select/all/claim")
    public List<Claim> selectAllClaim(@ApiParam(value = "当前页")int curPage,
                                    @ApiParam(value = "页大小")int pageSize){
        if(curPage<=0)curPage=1;
        if(pageSize<=0)pageSize=10;
        return claimService.selectAll(curPage,pageSize);
    }

    @ApiOperation(value = "按id查询claim数据", response = Claim.class)
    @GetMapping("/select/conditon/claim")
    public Claim selectClaimById(@ApiParam(value = "id")String id){
        return claimService.selectClaimById(id);
    }


    //改
    @ApiOperation(value = "修改单条text", response = String.class)
    @PostMapping("/update/text")
    public void updateText(@ApiParam(value = "修改内容")@RequestBody Text text){//注意id是不能修改的
        textService.update(text);
    }

    @ApiOperation(value = "修改单条claim", response = String.class)
    @PostMapping("/update/claim")
    public void updateClaim(@ApiParam(value = "修改内容")@RequestBody Claim claim){//注意id是不能修改的
        claimService.update(claim);
    }

}
