var fs = require('fs');
var express = require('express');
var app = express();

app.set('port', process.env.PORT || 8990);
app.use(express.static(__dirname + '/public'));

var port = app.get('port');
var defaultCharset = 'utf8';

/* Replaces 'localhost:8080' url with actual server url */
function replaceUrl(data, req) {
    var host = req.protocol + '://' + req.hostname + (port == 80 || port == 443 ? '' : ':' + port);
    return data.replace(/http:\/\/localhost:8080/g, host);
}

/* Schedule */
app.get('/schedule', function(req, res) {
    res.type('application/json; charset=' + defaultCharset);
    res.status(200).send(fs.readFileSync('data/schedule.json', defaultCharset));
});

/* Speakers */
app.get('/speakers', function(req, res) {
    res.type('application/json; charset=' + defaultCharset);
    res.status(200).send(fs.readFileSync('data/speakers.json', defaultCharset));
});

/* Default (home page) */
app.get('^*$', function(req, res) {
    var data = fs.readFileSync('data/index.html', defaultCharset);
    data = replaceUrl(data, req);

    res.type('text/html; charset=' + defaultCharset);
    res.status(200).send(data);
});

/* Starts server */
app.listen(port, function () {
    console.log('Express server listening on port ' + port);
});
