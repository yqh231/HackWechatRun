'use strict';

function submit() {
    const date = new Date().setHours(0, 0, 0, 0) / 1000;
    const steps = document.getElementsByName('steps')[0].value;
    const uid = document.getElementsByName('uid')[0].value;
    const pc_value = document.getElementsByName('pc_value')[0].value;
    const calories = steps, distance = steps, duration = steps;
    const key = md5(`ldl_pro@${date}#${steps}$${calories}^${distance}&${duration}`);
    const url = `http://walk.ledongli.cn/rest/dailystats/upload/v3?uid=${uid}`;
    const information = {
        calories: parseInt(calories),
        distance: parseInt(distance),
        duration: parseInt(duration),
        steps: parseInt(steps),
        key: key,
        date: date
    };
    const postData = `pc=${pc_value}&stats=[${JSON.stringify(information)}]`;
    axios.post(url, postData)
        .then(() => {
            swal({
                text: 'Submit successfully!',
                type: 'success'
            });
        })
        .catch(error => {
            swal({
                text: 'Ooops! There is something wrong.',
                type: 'error'
            });
        });
}
