<%--

    Copyright (C) 2012-2013  Olexandr Tyshkovets

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

--%>
<%-- 
  - Author:      Olexandr Tyshkovets
  - Description: This page displays the registration form.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><fmt:message key="registration.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validate.js"></script>
    <script type="text/javascript">
        window.onload = function() {
            populateDropDownDate("dayField", "monthField", "yearField");
        }
    </script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            <p>
                <span style="color: red;"><c:out value="${requestScope.error_msg}"/></span>
            </p>
            <div align="center">	
                <form action="${pageContext.request.contextPath}/registration" method="post" >
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="firstName" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.first_name" />:<br>
                    <input type="text" value="<c:out value="${requestScope.firstNameField}" />" name="firstNameField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, length: 2-30</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="lastName" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.last_name" />:<br>
                    <input type="text" value="<c:out value="${requestScope.lastNameField}" />" name="lastNameField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, length: 2-30</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="userName" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.username" />:<br>
                    <input type="text" value="<c:out value="${requestScope.userNameField}" />" name="userNameField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, 0-9, _, length: 3-20</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="email" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.email" />:<br>
                    <input type="text" value="<c:out value="${requestScope.emailField}" />" name="emailField"><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="password" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.password" />:<br>
                    <input type="password" name="passwordField"><br>
                    <span style="font-size: 60%; color: grey;">length: 4-50</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="rePassword" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.re_password" />:<br>
                    <input type="password" name="rePasswordField"><br>
                    <fmt:message key="registration.label.birthday" />:<br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="year" errorMsgMap="${requestScope.errorMsgMap}" /><br>
                        <jblog:printValidationErrorMsg fieldName="month" errorMsgMap="${requestScope.errorMsgMap}" /><br>
                        <jblog:printValidationErrorMsg fieldName="day" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <select id="yearField" size="1" name="yearField"></select>
                    <select id="monthField" size="1" name="monthField"></select>
                    <select id="dayField" size="1" name="dayField"></select>
                    <fieldset style="width: 80%; height: 400px;">
                        <legend style="color: red;"><fmt:message key="registration.label.terms_of_service" /></legend>
                        <div style="height: 390px; overflow: auto; text-align: justify;">
                            <h3>Terms of Service</h3>
                            <p>Thanks for using our website (“Website”). By using our Website, you are agreeing to these terms. Please read them carefully.</p>
                            <h4>Using our Website</h4>
                            <p>You must follow any policies made available to you within the Website.</p>
                            <p>Don’t misuse our Website. For example, don’t interfere with our Website or try to access them using a method other than the interface and the instructions that we 
                            provide. You may use our Website only as permitted by law, including applicable export and re-export control laws and regulations. We may suspend or stop providing 
                            our Website to you if you do not comply with our terms or policies or if we are investigating suspected misconduct.</p>
                            <p>Using our Website does not give you ownership of any intellectual property rights in our Website or the content you access. You may not use content from our 
                            Website unless you obtain permission from its owner or are otherwise permitted by law. These terms do not grant you the right to use any branding or logos used in 
                            our Website. Don’t remove, obscure, or alter any legal notices displayed in or along with our Website.</p>
                            <p>Our Website display some content that is not our. This content is the sole responsibility of the entity that makes it available. We may review content to determine 
                            whether it is illegal or violates our policies, and we may remove or refuse to display content that we reasonably believe violates our policies or the law. But that 
                            does not necessarily mean that we review content, so please don’t assume that we do.</p>
                            <p>In connection with your use of the Website, we may send you service announcements, administrative messages, and other information. You may opt out of some of 
                            those communications.</p>
                            <h4>Your Content in our Website</h4>
                            <p>Our Website allow you to submit content. You retain ownership of any intellectual property rights that you hold in that content. In short, what belongs to you 
                            stays yours.</p>
                            <p>When you upload or otherwise submit content to our Website, you give to us (and those we work with) a worldwide license to use, host, store, reproduce, modify, 
                            create derivative works (such as those resulting from translations, adaptations or other changes we make so that your content works better with our Website), 
                            communicate, publish, publicly perform, publicly display and distribute such content. The rights you grant in this license are for the limited purpose of operating, 
                            promoting, and improving our Website, and to develop new ones. This license continues even if you stop using our Website. Our Website sometimes may offer you ways 
                            to access and remove content that has been provided to our Website. Make sure you have the necessary rights to grant us this license for any content that you submit 
                            to our Website.</p>
                            <p>If you submit feedback or suggestions about our Website, we may use your feedback or suggestions without obligation to you.</p>
                            <h4>Our Warranties and Disclaimers</h4>
                            <p>We provide our Website using a commercially reasonable level of skill and care and we hope that you will enjoy using them. But there are certain things that we 
                            don’t promise about our Website.</p>
                            <p>Other than as expressly set out in these terms or additional terms, we make any specific promises about the Services. For example, we don’t make any commitments 
                            about the content within the Website, the specific functions of the Website, or they reliability, availability, or ability to meet your needs. We provide the 
                            Website “as is”.</p>
                            <p>Some jurisdictions provide for certain warranties, like the implied warranty of merchantability, fitness for a particular purpose and non-infringement. To the 
                            extent permitted by law, we exclude all warranties.</p>
                            <h4>About these Terms</h4>
                            <p>We may modify these terms or any additional terms that apply to a Website to, for example, reflect changes to the law or changes to our Website. You should look 
                            at the terms regularly. We’ll post notice of modified additional terms in the our Website. Changes will not apply retroactively and will become effective no sooner 
                            than fourteen days after they are posted. However, changes addressing new functions for a Website or changes made for legal reasons will be effective immediately. 
                            If you do not agree to the modified terms for our Website, you should discontinue your use of our Website.</p>
                            <p>If there is a conflict between these terms and the additional terms, the additional terms will control for that conflict.</p>
                            <p>These terms control the relationship between us and you. They do not create any third party beneficiary rights.</p>
                            <p>If you do not comply with these terms, and we don’t take action right away, this doesn’t mean that we are giving up any rights that we may have (such as taking 
                            action in the future).</p>
                            <p>If it turns out that a particular term is not enforceable, this will not affect any other terms.</p>
                            <p>The courts in some countries will not apply California law to some types of disputes. If you reside in one of those countries, then where California law is 
                            excluded from applying, your country’s laws will apply to such disputes related to these terms. Otherwise, you agree that the laws of California, U.S.A., 
                            excluding California’s choice of law rules, will apply to any disputes arising out of or relating to these terms or the Website. Similarly, if the courts in your 
                            country will not permit you to consent to the jurisdiction and venue of the courts in Santa Clara County, California, U.S.A., then your local jurisdiction and 
                            venue will apply to such disputes related to these terms. Otherwise, all claims arising out of or relating to these terms or the services will be litigated 
                            exclusively in the federal or state courts of Santa Clara County, California, USA, and you and us to personal jurisdiction in those courts.</p>
                            ------------------------------------------------------------------------------------------
                            <h3>Условия использования</h3>
                            <p>Благодарим за использование нашего интернет-ресурса (сайта) (далее «Ресурс»). Используя наш Ресурс, вы соглашаетесь с настоящими Условиями. Просим вас 
                            внимательно ознакомиться с ними.</p>
                            <h4>Использование Ресурса</h4>
                            <p>Вы должны соблюдать все правила, с которыми вам будет предложено ознакомиться при использовании Ресурса.</p>
                            <p>Не используйте Ресурс ненадлежащим образом. В частности, не пытайтесь вмешаться в их работу или получить к ним доступ в обход стандартного интерфейса и наших 
                            инструкций. Используйте Ресурс только в соответствии с нормами законодательства, включая применимые нормативные акты и правила относительно экспорта и реэкспорта. 
                            Если вы будете нарушать данные условия и правила или если мы заподозрим вас в этом, мы можем приостановить или полностью закрыть вам доступ к Ресурсу.</p>
                            <p>При работе с Ресурсом вам не предоставляются права на интеллектуальную собственность ни на сам Ресурс, ни на связанное с ним содержание. Последнее вы можете 
                            использовать только в том случае, если у вас есть разрешение его владельца или если такая возможность обеспечивается законодательством. Настоящие условия не 
                            предоставляют вам прав на использование каких-либо элементов брендинга или логотипов нашего Ресурса. Вы не должны удалять, скрывать или изменять юридические 
                            уведомления, отображаемые на страницах Ресурса.</p>
                            <p>В Ресурсе может быть представлено содержание, созданное и/или загруженное третьими лицами. Последние несут за него полную ответственность. Иногда мы проверяем 
                            содержание на предмет соответствия законодательству и нашим правилам. В случае выявления серьезных нарушений мы можем удалить или заблокировать его. Однако это 
                            не означает, что мы в обязательном порядке проверяем все материалы.</p>
                            <p>Если вы используете нашим Ресурсом, мы можем присылать вам уведомления, сообщения от администратора и другие информационные материалы. В отдельных случаях вы 
                            можете при необходимости отказаться от их получения.</p>
                            <h4>Ваше содержание в Ресурсе</h4>
                            <p>Наш Ресурс позволяє распространять содержание. При этом все права на интеллектуальную собственность в отношении этих материалов остаются у их владельца. 
                            Проще говоря, все, что было вашим, таковым и останется.</p>
                            <p>Загружая или иным образом добавляя материалы в наш Ресурс, вы предоставляете нам и нашим партнерам действующую во всем мире лицензию, которая позволяет нам 
                            использовать это содержание, размещать его, хранить, воспроизводить, изменять, создавать на его основе производные работы (например, переводы, адаптации и прочие 
                            способы оптимизации материалов), обмениваться им, публиковать его, открыто воспроизводить, отображать, а также распространять. Перечисленные права, которые вы 
                            предоставляете нам, используются исключительно для обеспечения работы Ресурса, эго продвижения и совершенствования, а также для разработки новых. Указанная 
                            лицензия будет действовать и после того, как вы откажетесь от использования Ресурса. Иногда в нашем Ресурсе разрешается просматривать и удалять добавленное в его 
                            содержание. Прежде чем отправлять нам содержание и тем самым предоставлять лицензию на его использование, убедитесь, что у вас есть на это соответствующие права.</p>
                            <p>Если вы отправляете нам отзывы и предложения относительно работы с Ресурсом, мы оставляем за собой право реализовывать их, не возлагая на себя никаких 
                            обязательств перед их автором.</p>
                            <h4>Гарантии и отказ от обязательств</h4>
                            <p>Мы предоставляем Ресурс исходя из экономически обоснованного уровня функциональности и поддержки. Надеемся, что вы оцените эго по достоинству. Однако мы не 
                            можем взять на себя некоторые обязательства, касающиеся нашего Ресурса.</p>
                            <p>Мы не даем никаких иных гарантий относительно Ресурса, кроме тех, которые указаны в представленных и дополнительных Условиях использования. В частности, мы не 
                            берем на себя никаких обязательств относительно содержания Ресурса, их особых функциональных возможностей, надежности, доступности и соответствия вашим потребностям. 
                            Ресурса предоставляются по принципу «как есть».</p>
                            <p>Законодательство некоторых стран обеспечивает такие гарантии, как товарность, пригодность к определенной сфере применения и отсутствие нарушений авторских прав. 
                            Кроме ситуаций, оговоренных законодательством, мы исключаем все подразумеваемые гарантии.</p>
                            <h4>О данных Условиях использования</h4>
                            <p>Мы оставляем за собой право изменять эти или дополнительные Условия использования в случае внесения поправок в законодательство или обновления самого Ресурса. 
                            Вам необходимо регулярно отслеживать информацию о таких корректировках. Информация об изменении дополнительных условий распространяется в рамках нашего Ресурса. 
                            Изменения не имеют обратного действия и вступают в силу не ранее четырнадцати дней с момента публикации. Однако если они связаны с внедрением новых функций Ресурса 
                            или внесением поправок в законодательство, то будут применяться немедленно. Если вы не согласны с изменениями в условиях использования Ресурса, вы должны 
                            прекратить работу с ней.</p>
                            <p>Если данные условия противоречат дополнительным, последние имеют приоритет.</p>
                            <p>Данные условия регулируют взаимоотношения между нами и вами. Они не предусматривают регулирование прав третьих лиц.</p>
                            <p>Если вы нарушаете данные условия и мы не предпринимаем немедленных действий, это не означает, что мы не намерены отстаивать свои права в будущем (в том числе 
                            предпринимать определенные действия).</p>
                            <p>Если одно из положений данных условий утратит силу, это не повлияет на легитимность остальных положений.</p>
                            <p>В некоторых случаях суды ряда стран не признают законодательство Калифорнии. Если вы проживаете в одном из таких регионов, законодательство Калифорнии не будет 
                            применяться при рассмотрении исков, связанных с данными Условиями использования. В противном случае вы соглашаетесь с тем, что все споры в отношении Условий 
                            использования или Ресурса будут разрешаться в соответствии с законодательством штата Калифорния (США), исключая положения о конфликте законов. Аналогично, если 
                            суд в вашей стране откажется признавать юрисдикцию и округ судов Санта-Клары (штат Калифорния, США), то иски, относящиеся к данным Условиям использования, должны 
                            рассматриваться в рамках местной юрисдикции и судебного округа. В противном случае все иски, так или иначе связанные с настоящими Условиями использования или самим 
                            Ресурсом, находятся в компетенции судов федерального уровня или уровня штата, расположенных в округе Санта-Клара (штат Калифорния, США). Вы наравне с нами признаете 
                            юрисдикцию этих судебных органов.</p>
                            ------------------------------------------------------------------------------------------
                            <h3>Умови використання</h3>
                            <p>Дякуємо, що користуєтеся нашим інтернет-ресурсом (сайтом) (надалі – «Ресурс»). Користуючись нашим ресурсом, ви приймаєте ці умови. Просимо уважно 
                            ознайомитися з ними.</p>
                            <h4>Використання Ресурсу</h4>
                            <p>Ви повинні дотримуватися положень будь-якої політики, представленої вам у межах Ресурсу.</p>
                            <p>Не зловживайте нашим Ресурсом. Зокрема, не перешкоджайте роботі Ресурсу і не намагайтеся отримати до них доступ за допомогою методів, які не відповідають 
                            наданим нами інтерфейсам і вказівкам. Нашим Ресурсом можна користуватися лише в дозволених законодавством межах, включно з чинними законами та нормативними 
                            актами про експорт і реекспорт. Ми можемо призупинити чи припинити надання вам Ресурсу у випадку недотримання вами наших положень і умов або політики, а також 
                            під час розгляду ваших імовірних зловживань.</p>
                            <p>Користування нашим Ресурсом не надає вам жодних прав інтелектуальної власності щодо нашого Ресурсу чи вмісту, доступ до якого ви отримуєте. Ви можете 
                            використовувати вміст, отриманий із нашого Ресурсу, виключно з дозволу його власника або в інших дозволених законодавством межах. Ці умови не дають вам права 
                            користуватися будь-якими елементами бренда чи логотипами нашого Ресурсу. Забороняється видаляти, приховувати чи змінювати будь-які юридичні застереження, що 
                            відображаються у нашому Ресурсі.</p>
                            <p>Частина відображеного в нашому Ресурсі вмісту не належить нам. Відповідальність за такий вміст несуть виключно суб’єкти, які його надають. Ми можемо перевіряти 
                            вміст з метою визначення того, чи не порушує він вимог законодавства або нашої політики. Також ми можемо видаляти вміст або відмовлятися його публікувати, якщо у 
                            нас є обґрунтовані підстави вважати, що він порушує наші правила чи є незаконним. Однак це не обов’язково означає, що ми перевіряємо вміст, тому не слід 
                            припускати, що ми це робимо.</p>
                            <p>У зв’язку з вашим користуванням Ресурсом ми можемо надсилати вам оголошення служб, адміністративні повідомлення й іншу інформацію. Ви можете відмовитися від 
                            деяких із таких повідомлень.</p>
                            <h4>Ваш вміст у нашому Ресурсі</h4>
                            <p>Наш Ресурс дозволяє вам надавати власний вміст. За вами зберігаються будь-які права інтелектуальної власності стосовно такого вмісту. Одним словом, усе, що 
                            належить вам, залишається у вашій власності.</p>
                            <p>Завантажуючи або іншим чином подаючи вміст у наш Ресурс, ви надаєте нам (і нашим партнерам) визнану в усьому світі ліцензію на використання, розміщення, 
                            зберігання, копіювання, зміну, повідомлення, публікацію, публічне відтворення й відображення та поширення такого вмісту, а також створення похідних матеріалів 
                            на його основі (як-от отриманих у результаті перекладу, адаптації чи інших внесених нами змін з метою кращої сумісності вашого вмісту з нашим Ресурсом). Надані 
                            вами в рамках такої ліцензії права застосовуються виключно з метою забезпечення роботи, рекламування й удосконалення нашого Ресурса, а також з метою розробки 
                            нових Ресурсів. Ця ліцензія залишатиметься чинною навіть у тому випадку, якщо ви припините користування нашим Ресурсом. Наш Ресурс інколи може пропонувати способи 
                            отримання доступу до поданого в наш Ресурс вмісту чи його видалення. Переконайтеся, що ви володієте всіма необхідними правами на надання нам такої ліцензії для 
                            будь-якого вмісту, поданого вами в наш Ресурс.</p>
                            <p>Якщо ви надсилаєте свої відгуки чи пропозиції стосовно нашого Ресурсу, ми можемо використовувати такі відгуки чи пропозиції без жодних зобов’язань перед вами.</p>
                            <h4>Наші гарантії та застереження</h4>
                            <p>Ми надаємо Ресурс, дотримуючись комерційно обґрунтованого рівня умінь і турботи, і сподіваємося, що ви залишитеся задоволені, користуючись нашим Ресурс. Однак 
                            є певні речі, які ми не можемо гарантувати в нашому Ресурсі.</p>
                            <p>За винятком положень, прямо викладених у цих або додаткових умовах, ми не надаємо жодних спеціальних гарантій щодо Ресурсу. Наприклад, ми не несемо жодних 
                            зобов’язань щодо вмісту в рамках Ресурсу, певних функціональних можливостей Ресурсу, його надійності, доступності або здатності задовольняти ваші потреби. Ресурс 
                            надаються нами «як є».</p>
                            <p>Деякі юрисдикції передбачають певні гарантії, наприклад, опосередковану гарантію придатності для продажу, відповідності певній меті та непорушення прав 
                            інтелектуальної власності. Наскільки це дозволено законодавством, ми відмовляємося від усіх гарантій.</p>
                            <h4>Про ці Умови</h4>
                            <p>Ми можемо змінювати ці чи будь-які додаткові умови, що стосуються нашого Ресурсу, наприклад, з метою відображення змін у законодавстві чи в самому Ресурсі. 
                            Слід регулярно переглядати ці умови. Примітки про оновлення додаткових умов публікуватимуться на нашому Ресурсі. Зміни не матимуть зворотної сили та почнуть діяти 
                            не раніше ніж через чотирнадцять днів після їх публікації. Однак зміни, які стосуються нових функцій Ресурсу, чи зміни, внесені з правових причин, починатимуть 
                            діяти негайно. Якщо ви не погоджуєтеся зі зміненими умовами використання, необхідно припинити ваше користування Ресурсом.</p>
                            <p>У разі виникнення суперечностей між цими та додатковими умовами перевагу матимуть додаткові умови.</p>
                            <p>Ці умови регулюють відносини між вами та нами. Вони не створюють жодних прав третіх осіб на пільги чи привілеї.</p>
                            <p>Якщо ви порушили ці умови, однак ми не вжили негайних заходів, це не означає, що ми відмовляємося від певних прав, які можемо мати (наприклад, права вживати 
                            відповідних заходів у майбутньому).</p>
                            <p>Якщо певне положення припиняє діяти, це не впливає на будь-які інші положення цього документа.</p>
                            <p>Суди деяких країн не застосовують законодавство штату Каліфорнія для вирішення певних типів спорів. Якщо ви проживаєте в одній із таких країн, у випадках 
                            неможливості застосування законодавства штату Каліфорнія до таких спорів, пов’язаних із цими умовами, застосовуватиметься законодавство вашої країни. В іншому 
                            випадку ви погоджуєтеся, що до будь-яких спорів, які виникають із цих умов або пов’язані з ними чи Ресурсом, застосовується законодавство штату Каліфорнія, США, 
                            окрім норм колізійного права цього штату. Аналогічно, якщо за рішенням суду своєї країни ви не можете підпорядковуватись юрисдикції чи територіальній підсудності 
                            судів округу Санта-Клара, штат Каліфорнія, США, для вирішення спорів, які пов’язані з цими умовами, може застосовуватися місцева юрисдикція чи територіальна 
                            підсудність. В іншому випадку всі претензії, що виникають із цих умов або пов’язані з ними чи Ресурсом, будуть розглядатися виключно федеральними судами чи судами 
                            штату в окрузі Санта-Клара, штат Каліфорнія, США, і ви та ми погоджуєтеся на персональну юрисдикцію таких судів.</p>
                        </div>
                    </fieldset>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="license" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.accept_the_terms" /> <input name="acceptLicenseField" type="checkbox"><br>
                    <input type="submit" value="<fmt:message key="registration.button.sign_up" />" name="registrationButton">
                </form>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
