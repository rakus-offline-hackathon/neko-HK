var array = [];
var count = 0;
var currentCode = [];
var currentCodeCount = 0;
var result = false;
var cat = false;

// start時の処理
function clickStart() {
  initData();
  renderCode();
  $(window).on('keypress', keyPress);
}

// データの初期化
function initData() {
  array = _.map(getCode(), function(data) {
    return {
      title: data.title,
      code: data.code.split(''),
      result: data.result
    }
  });
}

// ソースコード描画
function renderCode() {
  if (!cat && array[count]) {
    // タイトルの表示
    $('#title').html(array[count].title);
    // コードの表示
    currentCode = array[count].code;
    $('#code').html(_.map(currentCode, function(code, e) {
      var str = '';
      if (e < currentCodeCount) {
        str += '<span class="finish">';
      } else {
        str += '<span>';
      }
      return str + code + '</span>';
    }));
  }

}

// 問題のソースコードを取得
function getCode() {
    // $.ajax({
    //     type:"get",
    //     url:"http://localhost:8080/data",
    //     contentType: 'application/json',
    //     dataType: "json",
    //     success: function(data) {
    //       return data;
    //     }
    // });
	var data = [{"title": "title1", "code": "code1", "result": "result1"},{"title": "title2", "code": "code2", "result": "result2"}];
  return data;
}

// キー入力された時の処理
function keyPress(e) {
  if (cat) {
    return;
  }
  if(String.fromCharCode(e.keyCode) === currentCode[currentCodeCount]) {
    currentCodeCount++;
    renderCode();
  } else {
    console.log('不正解');
  }
  // Enter押したら結果を表示
  if (e.keyCode === 13) {
    if (result) {
      count++;
      // 初期化
      currentCodeCount = 0;
      result = false;
      $('#result').html('');
      if (!array[count]) {
        renderCat();
      } else {
        renderCode();
      }
    }
    if (currentCodeCount === currentCode.length && !result) {
      $('#result').html(array[count].result);
      result = true;
    }
  }
}

// 最後の画面表示
function renderCat() {
  $('#input').hide();
  $('#title').hide();
  $('#code').hide();
  $('#cat').show();
  cat = true;
}
