<%
    /**
     * Copyright 2013 Sean Kavanagh - sean.p.kavanagh6@gmail.com
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     * http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="../_res/inc/header.jsp"/>

    <script type="text/javascript">
        $(document).ready(function() {

            $('#runCmd_command').focus();

            $("#set_password_dialog").dialog({
                autoOpen: false,
                height: 200,
                minWidth: 500,
                modal: true
            });
            $("#error_dialog").dialog({
                autoOpen: false,
                height: 200,
                width: 500,
                modal: true
            });
            $("#upload_push_dialog").dialog({
                autoOpen: false,
                height: 350,
                width: 530,
                modal: true,
                open: function(event, ui) { $(".ui-dialog-titlebar-close").show(); },
                close: function() {
                    $("#upload_push_frame").attr("src", "");
                    $('#runCmd_command').focus();
                }
            });

            $(".enter_btn").button().click(function() {
                $(this).prev().submit();
                $('#runCmd_command').focus();
            });

            //submit add or edit form
            $(".submit_btn").button().click(function() {
                <s:if test="pendingSystemStatus!=null">
                $(this).prev().submit();
                </s:if>
                 $("#error_dialog").dialog("close");
                $('#runCmd_command').focus();
            });
            //close all forms
            $(".cancel_btn").button().click(function() {
                $("#set_password_dialog").dialog("close");
                window.location = 'getNextPendingSystemForTerms.action?pendingSystemStatus.id=<s:property value="pendingSystemStatus.id"/>&script.id=<s:if test="script!=null"><s:property value="script.id"/></s:if>';
                $('#runCmd_command').focus();

            });

            //function for command keys (ie ESC, CTRL, etc..)
            var keys = {};
            $("#runCmd_command").keydown(function(e) {
                keys[e.which] = true;
                var c = String.fromCharCode(e.which);
                if (keys[27] || keys[37] || keys[38] || keys[39] || keys[40] || keys[17]) {


                    var ids = [];
                    $(".run_cmd_active").each(function(index) {
                        var id = $(this).attr("id").replace("run_cmd_", "");
                        ids.push(id);
                    });
                    var idListStr = '';
                    ids.forEach(function(entry) {
                        idListStr = idListStr + '&idList=' + entry;
                    });
                    $.ajax({ url: 'runCmd.action?keyCode=' + e.which + idListStr});


                    $('#runCmd_command').val('');
                    $('#runCmd_command').focus();
                }

            });
            $(document).keyup(function (e) {
                delete keys[e.which];
            });

            //run command
            $('#runCmd').submit(function() {


                var ids = [];
                $(".run_cmd_active").each(function(index) {
                    var id = $(this).attr("id").replace("run_cmd_", "");
                    ids.push(id);
                });
                var idListStr = '';
                ids.forEach(function(entry) {
                    idListStr = idListStr + '&idList=' + entry;
                });
                $.ajax({ url: 'runCmd.action?command=' + $('#runCmd_command').val() + idListStr});


                $('#runCmd_command').val('');
                $('#runCmd_command').focus();
                return false;

            });

            //if terminal window toggle active for commands
            $(".run_cmd").click(function() {
               //de-active or active clicked terminal
               if ($(this).hasClass('run_cmd_active')) {
                    $(this).removeClass('run_cmd_active')
                } else {
                    $(this).addClass('run_cmd_active')
                }

                $('#runCmd_command').focus();
            });

            $('#select_all').click(function() {
                $(".run_cmd").addClass('run_cmd_active');
                $('#runCmd_command').focus();
            });

            $('#unselect_all').click(function() {
                $(".run_cmd").removeClass('run_cmd_active');
                $('#runCmd_command').focus();
            });

            $('#upload_push').click(function() {


                //get id list from selected terminals
                var ids = [];
                $(".run_cmd_active").each(function(index) {
                    var id = $(this).attr("id").replace("run_cmd_", "");
                    ids.push(id);
                });
                var idListStr = '?action=upload';
                ids.forEach(function(entry) {
                    idListStr = idListStr + '&idList=' + entry;
                });

                $("#upload_push_frame").attr("src", "/manage/setUpload.action" + idListStr);
                $("#upload_push_dialog").dialog("open");


            });


            <s:if test="pendingSystemStatus!=null">
            <s:if test="pendingSystemStatus.statusCd==\"A\"">
            $("#set_password_dialog").dialog("open");
            </s:if>
            <s:else>
            <s:if test="currentSystemStatus==null||currentSystemStatus.statusCd==\"P\" ||currentSystemStatus.statusCd!=\"F\"">
            setInterval(function() {
                $("#composite_terms_frm").submit();
            }, 2000);
            </s:if>
            </s:else>
            </s:if>


            <s:if test="currentSystemStatus!=null">
            <s:if test="currentSystemStatus.statusCd==\"F\"">
            $("#error_dialog").dialog("open");
            </s:if>
            </s:if>

            <s:if test="pendingSystemStatus==null">

            setInterval(function() {
                $.getJSON('getOutputJSON.action', function(data) {
                    var append = false;
                    $.each(data, function(key, val) {
                        if (val.output != '') {
                            $('#output_' + val.sessionId).append(val.output);
                            $('#scroll_' + val.sessionId).scrollTop($("#output_" + val.sessionId).height());
                        }
                    });
                    //scroll to bottom
                });
            }, 750);
            </s:if>


        });
    </script>
    <style type="text/css">
        .content {
            width: 100%;
        }
    </style>

    <title>KeyBox - Composite Terms</title>

</head>
<body>

<div class="page">

    <s:if test="(schSessionMap!= null && !schSessionMap.isEmpty()) || pendingSystemStatus!=null">
        <div class="content">

        <s:if test="pendingSystemStatus==null">
            <ul class="top_nav">
                <li class="top_nav_item">
                    <s:form id="runCmd" action="runCmd" cssClass="runCmd" theme="simple">
                        #&nbsp;<s:textfield id="runCmd_command" name="command" theme="simple"
                                            cssClass="runCmd_command" size="35"/>
                        <div id="enter_btn" class="enter_btn">Enter</div>
                    </s:form>
                </li>

                <li class="top_nav_item"><a href="/manage/exitTerms.action">Exit Terminals</a></li>
            </ul>
            <div class="clear"></div>
            <br/>

            <div id="select_all">Select All</div>
            <div id="unselect_all">Unselect All</div>
            <div id="upload_push">Upload &amp; Push</div>
            <div class="clear"></div>

            </s:if>
            <div class="scrollwrapper">
                <s:iterator value="schSessionMap">
                    <div id="run_cmd_<s:property value="key"/>" class="run_cmd_active run_cmd">

                        <h4><s:property value="value.hostSystem.displayLabel"/></h4>

                        <div id="scroll_<s:property value="key"/>" class="scroll">
                            <pre id="output_<s:property value="key"/>" class="output"></pre>
                        </div>
                    </div>
                </s:iterator>
            </div>


            <div id="set_password_dialog" title="Set Password">
                <p class="error"><s:property value="pendingSystemStatus.errorMsg"/></p>

                <p>Set password for <s:property value="pendingSystemStatus.hostSystem.displayLabel"/>

                </p>
                <s:form id="password_frm" action="createTerms">
                    <s:hidden name="pendingSystemStatus.id"/>
                    <s:password name="password" label="Password" size="15" value="" autocomplete="off"/>
                    <s:if test="script!=null">
                        <s:hidden name="script.id"/>
                    </s:if>
                </s:form>
                <div class="submit_btn">Submit</div>
                <div class="cancel_btn">Cancel</div>
            </div>


            <div id="error_dialog" title="Error">
                <p class="error">Error: <s:property value="currentSystemStatus.errorMsg"/></p>

                <p>System: <s:property value="currentSystemStatus.hostSystem.displayLabel"/>

                </p>

                <s:form id="error_frm" action="createTerms">
                    <s:hidden name="pendingSystemStatus.id"/>
                    <s:if test="script!=null">
                        <s:hidden name="script.id"/>
                    </s:if>
                </s:form>
                <div class="submit_btn">OK</div>
            </div>

            <div id="upload_push_dialog" title="Upload &amp; Push">
                <iframe id="upload_push_frame" width="500px" height="300px" style="border: none;">

                </iframe>


            </div>


            <s:form id="composite_terms_frm" action="createTerms">
                <s:hidden name="pendingSystemStatus.id"/>
                <s:if test="script!=null">
                    <s:hidden name="script.id"/>
                </s:if>
            </s:form>

        </div>
    </s:if>
    <s:else>
        <jsp:include page="../_res/inc/navigation.jsp"/>

        <div class="content" style="width: 70%">
            <p class="error">No sessions could be created</p>
        </div>
    </s:else>

</div>

</body>
</html>
