/*
 * blueimp Gallery Demo JS 2.11.1
 * https://github.com/blueimp/Gallery
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global blueimp, $ */

$(function () {
    'use strict';

    // Initialize the Gallery as video carousel:
    blueimp.Gallery([
		{
            title: 'Sintel-52player.com',
            href: 'http://39.108.230.90:8888/ivplay1/M00/00/00/rBKpa1oavZ6AKJxFAHT0bwDy1Q8036.mp4',
            type: 'video/mp4',
            poster: 'switchPlayer/images/1.jpg'
        }
     
    ], {
        container: '#blueimp-video-carousel',
        carousel: true
    });
	
	var playPause = $('#blueimp-video-carousel'); //播放和暂停
	
	playPause.on('click', function() {
		
	});

});
