/**
 * Created by tpeng on 2019-12-18.
 * 上传照片即时预览 插件
 * dependences : {}
 */
(function($) {

    $.fn.MyUploadImageView = function(options) {
        var opts = $.extend({}, $.fn.MyUploadImageView.defaults, options);
        return initUploadArea(opts, $(this));
    };

    $.fn.MyUploadImageView.defaults = {
        imgAreaId: '',
        imgWidth: 110,
        imgHeight: 140,
        isfBorder: false,
        defImgSrc: ''
    };


    function initUploadArea(opts, $rect){
        var fileBtn = '<input type="file" accept="image/*" style="display: none;"/>';
        $rect.append(fileBtn);
        var $fileJDom = $(fileBtn);

        //初始化默认无图
        var $img_ = initImg(opts);
        $img_.attr('src', opts.defImgSrc ? opts.defImgSrc :'/img/no_pic.png');

        //绑定事件
        var file;
        $fileJDom.on('change', function () {
            file = $(this).prop('files')[0];
            var fileReader = new FileReader();
            // 将文件对象传递给内置对象
            fileReader.readAsDataURL(file); //需要onload回调函数执行读取数据后的操作
            // 将读取出文件对象替换到img标签
            fileReader.onload = function(){  // 等待文件阅读器读取完毕再渲染图片
                var $img = initImg(opts);
                $img.attr('src', fileReader.result)
            }
        });

        $rect.on('click', function () {
            $fileJDom.click();
        });

        return {
            $thiz: $rect,
            $fileJDom: $fileJDom,
            getFile: function () {
                return file;
            }
        };
    }

    function initImg(opts) {
        var $img = $(opts.imgAreaId).find("img[_upv]");
        if($(opts.imgAreaId).find("img[_upv]").length == 0){
            $img = $('<img _upv />');
            if(opts.isfBorder){
                var $border = $('<div style="border: 1px solid #ccc;" />');
                $border.css({
                    width: opts.imgWidth + 1,
                    height: opts.imgHeight + 1
                });
                $border.append($img);
                $(opts.imgAreaId).append($border);
            }else {
                $(opts.imgAreaId).append($img);
            }
        }
        $img.css({
            width: opts.imgWidth,
            height: opts.imgHeight
        });
        return $img;
    }

})(jQuery);


