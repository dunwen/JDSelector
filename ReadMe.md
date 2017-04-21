#Selector

## 简单说明
项目是根据[JDAddressSelector](https://github.com/chihane/JDAddressSelector)修改而来的。  

- 支持多级自定义选择
- 去掉原项目依赖的dbflow、gson
- 去掉原项目仅支持地址一类选择，把选择类型放开（原项目写死了只能选择地址）。


## 效果
![](https://github.com/dunwen/JDSelector/blob/master/capture.gif)

## 使用

### 使用原始视图
```
		//deep 就是自定义的深度
        Selector selector = new Selector(this, deep);

        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                //根据tab的深度和前一项选择的id，获取下一级菜单项
                Log.i(TAG, "provideData: currentDeep >>> "+currentDeep+" preId >>> "+preId);
                receiver.send(getDatas());
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                String result = "";
                for (ISelectAble selectAble : selectAbles) {
                    result += selectAble.getName()+" ";
                }
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        });
        
        
//  	View view = selector.getView();
// 		frameLayout.addView(view)
// 		new AlertDialog.Builder(context).setView(view).show()
// 		...

```

### BottomDialog

```
        BottomDialog dialog = new BottomDialog(this);
        dialog.init(this,selector); //selector 按照上面的方法初始化
        dialog.show();
```

## 许可证
edit from [JDAddressSelector](https://github.com/chihane/JDAddressSelector)   
[MIT License](http://chihane.in/license)
