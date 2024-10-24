const nameInput = document.getElementById('name');
const emailInput = document.getElementById('email');
const telInput = document.getElementById('telephone');
const messageInput = document.getElementById('message');

const submit = document.getElementById('submit');

submit.addEventListener('click', function(event)
{
    if(nameInput.value == '' || emailInput.value == '' || telInput.value == '')
    {
        event.preventDefault();
        alert("Name, Email, or Telephone cannot be empty!");
    }
});