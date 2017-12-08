package com.taotao.pojo;
//VO（View Object）：视图对象，用于展示层，
// 它的作用是把某个指定页面（或组件）的所有数据封装起来。
//${item.images[0]}
public class ItemVo extends TbItem {
    public ItemVo(TbItem tbItem){
        //初始化属性
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }
    public String[] getImages(){
        String img = this.getImage();
        if (img!=null&&!"".equals(img)){
            return img.split(",");
        }
        return null;
    }
}
