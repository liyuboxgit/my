-----------------drag
	<div id="box"></div>
	#box {
		position: absolute;
		width: 100px;
		height: 100px;
		top: 10px;
		left: 10px;
		background: red;
	}
	let box = document.getElementById('box');
    document.onmousedown = function (e) {
		let disx = e.pageX - box.offsetLeft;
		let disy = e.pageY - box.offsetTop;
		document.onmousemove = function (e) {
			box.style.left = e.pageX - disx + 'px';
			box.style.top = e.pageY - disy + 'px';
		}
		//释放鼠标按钮，将事件清空，否则始终会跟着鼠标移动
		document.onmouseup = function () {
			document.onmousemove = document.onmouseup = null;
		}
	}
<<----end-----vue
详见H:\HbuilderX\vuedemo\readme.md

