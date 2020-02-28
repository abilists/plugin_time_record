function clearBgColumn(parentId) {

	var parentId = document.getElementById(parentId);
	var nodes = parentId.children;

    for (j = 0; j < nodes.length; j++) {
        var node = nodes[j].children;
        for (k = 0; k < node.length; k++) {
            node[k].style.backgroundColor = '';
        }
    }

    return nodes;
}

/*
 skipTag skip tag
*/
function setBgColumn(nodes, selectedNum, color, skipTag) {
	var i;
    for (i = 0; i < nodes.length; i++) {

    	if(nodes[i].tagName == skipTag) {
        	console.log("Skipped " + nodes[i].tagName);
    		continue;
    	}

        nodes[i].children[selectedNum].style.backgroundColor = color;
        nodes[i].children[selectedNum].style.borderWidth = '0px 1px 0px 1px';
    }
}

function clearBackGroundColor(tr) {
	for(var j=0; j< tr.length; j++) {
		tr[j].style.backgroundColor = "";
	}
}