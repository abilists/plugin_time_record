(function (factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as anonymous module.
    define(['jquery'], factory);
  } else if (typeof exports === 'object') {
    // Node / CommonJS
    factory(require('jquery'));
  } else {
    // Browser globals.
    factory(jQuery);
  }
})(function ($) {

// Comment for IE10
//  'use strict';

  var console = window.console || { log: function () {} };

  function CropAvatarBg($element) {
    this.$container = $element;

    this.$avatarView = this.$container.find('.avatar-view-bg');
    this.$avatar = this.$avatarView.find('img');
    this.$avatarModal = this.$container.find('#avatar-modal-bg');
    this.$loading = this.$container.find('.loading');

    this.$avatarUpload = this.$avatarModal.find('.avatar-upload-bg');
    this.$avatarSrc = this.$avatarModal.find('.avatar-src-bg');
    this.$avatarData = this.$avatarModal.find('.avatar-data-bg');
    this.$avatarInput = this.$avatarModal.find('.avatar-input-bg');
    this.$avatarSave = this.$avatarModal.find('.avatar-save-bg');
    this.$avatarBtns = this.$avatarModal.find('.avatar-btns-bg');

    this.$avatarWrapper = this.$avatarModal.find('.avatar-wrapper-bg');

    this.init();
  }

  CropAvatarBg.prototype = {
    constructor: CropAvatarBg,

    support: {
      fileList: !!$('<input type="file">').prop('files'),
      blobURLs: !!window.URL && URL.createObjectURL,
      formData: !!window.FormData
    },

    init: function () {
      this.support.datauri = this.support.fileList && this.support.blobURLs;

      console.log("initBg()");

      this.initTooltip();
      this.initModal();
      this.addListener();
    },

    addListener: function () {
      this.$avatarView.on('click', $.proxy(this.click, this));
      this.$avatarInput.on('change', $.proxy(this.change, this));
      this.$avatarBtns.on('click', $.proxy(this.rotate, this));
    },

    initTooltip: function () {
      this.$avatarView.tooltip({
        placement: 'bottom'
      });
    },

    initModal: function () {
      this.$avatarModal.modal({
        show: false
      });
    },

    click: function () {
      this.$avatarModal.modal('show');
    },

    syncUpload: function () {
    	console.log("avatarSaveBg.click()");
        this.$avatarSave.click();
    },

    change: function () {
      var files;
      var file;

      if (this.support.datauri) {
        files = this.$avatarInput.prop('files');

        if (files.length > 0) {
          file = files[0];

          if (isImageFile(file)) {
            if (this.url) {
              URL.revokeObjectURL(this.url); // Revoke the old one
            }

            this.url = URL.createObjectURL(file);
            this.startCropper();
          }
        }
      } else {
        file = this.$avatarInput.val();

        if (isImageFile(file)) {
          this.syncUpload();
        }

      }
    },

    rotate: function (e) {
      var data;

      if (this.active) {
        data = $(e.target).data();

        if (data.method) {
          this.$img.cropper(data.method, data.option);
        }
      }
    },
    zoom: function (e) {
      var data;
  	  if (e.ratio > e.oldRatio) {
  		// Prevent zoom in
  		this.$img.cropper(data.method, data.option);
  	  } else {
  		// zoom out
  		this.$img.cropper(data.method, data.option);
  	  }
    },
    startCropper: function () {
      var _this = this;

      if (this.active) {
        this.$img.cropper('replace', this.url);
      } else {

        this.$img = $('<img id="profileImgBg" src="' + this.url + '">');
        this.$avatarWrapper.empty().html(this.$img);
        this.$img.cropper({
          aspectRatio: 1,
          crop: function (e) {
              var json = [
                    '{"x":' + e.x,
                    '"y":' + e.y,
                    '"height":' + e.height,
                    '"width":' + e.width,
                    '"rotate":' + e.rotate + '}'
                  ].join();

              _this.$avatarData.val(json);
            },
  	      aspectRatio: 2,
	      viewMode: 1,
	      dragMode: 'move',
	      autoCropArea: 0.8,
	      restore: false,
	      guides: true,
	      highlight: false,
	      cropBoxMovable: true,
	      cropBoxResizable: false
        });

        this.active = true;
      }

      this.$avatarModal.one('hidden.bs.modal', function () {
        _this.stopCropper();
      });
    },

    stopCropper: function () {
      if (this.active) {
        this.$img.cropper('destroy');
        this.$img.remove();
        this.active = false;
      }
    },

    cropDone: function () {
      this.$avatar.attr('src', this.url);
      this.stopCropper();
      this.$avatarModal.modal('hide');
    },

    alert: function (msg) {
      var $alert = [
            '<div class="alert alert-danger avatar-alert-bg alert-dismissable">',
              '<button type="button" class="close" data-dismiss="alert">&times;</button>',
              msg,
            '</div>'
          ].join('');

      this.$avatarUpload.after($alert);
    }
  };

  $(function () {
    return new CropAvatarBg($('#crop-avatar-bg'));
  });

});
