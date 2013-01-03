/*
 * Copyright (C) 2012-2013  Olexandr Tyshkovets
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
function validateRegForm(form) {
	if (form.firstName.value == '' || form.lastName.value == '' || form.userName.value == '' || form.userEmail.value == '' ||
			form.userPassword.value == '' || form.userRePassword.value == '' || 
			form.year.value == '' || form.month.value == '' || form.day.value == ''|| form.acceptLicense.checked != true) {
		alert('Require to fill all fields');
        return false;
    } 
    if (/^[a-zA-Zа-яА-Я]{2,30}$/.test(form.firstName.value) == false) { 
    	alert('Uncorrect first name');
    	form.firstName.focus();
    	return false;
    }
    if (/^[a-zA-Zа-яА-Я]{2,30}$/.test(form.lastName.value) == false) { 
    	alert('Uncorrect last name');
    	form.lastName.focus();
    	return false;
    }
    if (/^[а-яА-Я\w]{3,20}$/.test(form.userName.value) == false) {
    	alert('Uncorrect user name');
    	form.userName.focus();
    	return false;
    }
    if (/^[\w-\.]{4,25}@([a-zA-Z]{1,20}\.){1,2}[a-z]{2,3}$/.test(form.userEmail.value) == false) { 
    	alert('Uncorrect email');
    	form.userEmail.focus();
    	return false;
    }
    if (form.userPassword.value.length < 4 || form.userPassword.value.length > 100) {
    	alert('Passwords to short');
    	form.userPassword.focus();
    	return false;
    }
    if (form.userPassword.value != form.userRePassword.value) {
    	alert('Passwords not equals');
    	form.userPassword.focus();
    	return false;
    }
    if (form.year.value == 'Select year') {
    	alert('Select year');
    	form.year.focus();
    	return false;
    }
    if (form.month.value == 'Select month') {
    	alert('Select month');
    	form.month.focus();
    	return false;
    }
    if (form.day.value == 'Select day') {
    	alert('Select day');
    	form.day.focus();
    	return false;
    }
    
    return true;
}

function validateAddStoryForm(form) {
	if (form.storyName.value == '' || form.storyPreview.value == '' || form.storyText.value == '' || form.storyKeyWords.value == '') {
		alert('Some field is empty');
        return false;
    }
	
    if (/^.{5,100}$/.test(form.storyName.value) == false) { 
    	alert('Uncorrect story name');
    	form.storyName.focus();
    	return false;
    }
    
    if ((form.storyPreview.value.length > 100 && form.storyPreview.value.length < 750) == false) { 
    	alert('Uncorrect story preview');
    	form.storyPreview.focus();
    	return false;
    }
    if ((form.storyText.value.length > 1500 && form.storyText.value.length < 2000000000) == false) { 
    	alert('Uncorrect story text');
    	form.storyText.focus();
    	return false;
    }  
    /*
    if (/^(\s).{100,750}$/.test(form.storyPreview.value) == false) { 
    	alert('Uncorrect story preview');
    	form.storyPreview.focus();
    	return false;
    }
    if (/^(\s).{500,2000000000}$/.test(form.storyText.value) == false) { 
    	alert('Uncorrect story text');
    	form.storyText.focus();
    	return false;
    }
    */
    if (/^.{5,115}$/.test(form.storyKeyWords.value) == false) {
    	alert('Uncorrect story key words');
    	form.storyKeyWords.focus();
    	return false;
    }
    
    return true;
}

function validateAddCommentForm(form) {
	if (form.commentText.value == '') {
		alert('comment cannot be empty');
        return false;
    }	
    if (form.commentText.value.length < 3 || form.commentText.value.length > 5000) { 
    	alert('Comment is too short or too long');
    	form.commentText.focus();
    	return false;
    }
    if (form.captchaAnswer.value.length < 4 || form.captchaAnswer.value.length > 15) { 
    	alert('Captcha answer is too short or too long');
    	form.captchaAnswer.focus();
    	return false;
    } 
			
	return true;
}

function validateAddMessageForm(form) {
	if (form.messageAuthor.value == '' || form.messageText.value == '' || form.captchaAnswer.value == '') {
		alert('Some field is empty');
        return false;
    }
	
    if (/^[а-яА-Я\w]{3,20}$/.test(form.messageAuthor.value) == false) { 
    	alert('Uncorrect name');
    	form.messageAuthor.focus();
    	return false;
    }
    if (form.messageText.value.length < 3 || form.messageText.value.length > 5000) { 
    	alert('Message is too short or too long');
    	form.messageText.focus();
    	return false;
    }
    if (!(/^.{4,15}$/.test(form.captchaAnswer.value))) { 
    	alert('Captcha answer is too short or too long');
    	form.captchaAnswer.focus();
    	return false;
    } 
		
	return true;
}

/**
 * validate userName and userPassword from loginForm
 * @returns {Boolean}
 */
function validateNameAndPassword(){
	switch (validateField(document.forms.loginForm.userName.value)) {
		case 1:
			alert("name is empty");
			document.forms.loginForm.userName.focus();
			return false;
		case 2:
			alert("name is short");
			document.forms.loginForm.userName.focus();
			return false;
		case 3:
			alert("name is long");
			document.forms.loginForm.userName.focus();
			return false;
	}
	switch (validateField(document.forms.loginForm.userPassword.value)) {
		case 1:
			alert("password is empty");
			document.forms.loginForm.userPassword.focus();
			return false;
		case 2:
			alert("password is short");
			document.forms.loginForm.userPassword.focus();
			return false;
		case 3:
			alert("password is long");
			document.forms.loginForm.userPassword.focus();
			return false;
	}
	
	return true;
}

/**
 * validate field
 * @param arg
 * @returns: 0 - OK, 1 - empty, 2 - short, 3 - long
 */
function validateField(arg){
	if (arg == "") {
		return 1;
	}
	if (arg.length < 3){
		return 2;
	}
	if (arg.length > 20){
		return 3;
	}
	return 0;
}

/**
 * validate field with multipliers
 * @param arg0 - text field
 * @param arg1 - multiplier(4*arg1) min length
 * @param arg2 - multiplier(50*arg1) max length
 * @returns: 0 - OK, 1 - empty, 2 - short, 3 - long
 */
function validateFieldM(arg0, arg1, arg2){
	if (arg0 == "") {
		return 1;
	}
	if (arg0.length < 5 * arg1){
		return 2;
	}
	if (arg0.length > 50 * arg2){
		return 3;
	}
	return 0;
}

