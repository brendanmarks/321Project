  
<template>
  <div id="id">
    <div class="site-wrap">
      <div class="site-section" style="padding-top:10%">
        <div class="container" style="align:center">
          <h1 class="page-title">Coop Reminders</h1>
          <div id="table">
            <table class="info-table" style="width: 100%; height: 100%;">
              <tr>
                <th style="padding:10px">Coop ID</th>
                <th style="padding:10px">Semester</th>
                <th style="padding:10px">Start Date</th>
                <th style="padding:10px">End Date</th>
                <th style="padding:10px">Student Name</th>
                <th style="padding:10px">McGill ID</th>
                <th style="padding:10px">Employer Name</th>
                <th style="padding:10px">Company</th>
              </tr>
              <tr>
                <td style="padding:10px">{{ coop.coopId }}</td>
                <td style="padding:10px">{{ coop.semester }}</td>
                <td style="padding:10px">{{ coop.startDate }}</td>
                <td style="padding:10px">{{ coop.endDate }}</td>
                <td style="padding:10px">{{ coop.student.firstName + " " + coop.student.lastName }}</td>
                <td style="padding:10px">{{ coop.student.id }}</td>
                <td
                  style="padding:10px"
                >{{ coop.employer.firstName + " " + coop.employer.lastName }}</td>
                <td style="padding:10px">{{ coop.employer.company }}</td>
              </tr>
            </table>
            <table class="info-table" style="width: 100%; height: 100%;">
              <tr>
                <th style="padding:5px">Urgency</th>
                <th style="padding:5px">Subject</th>
                <th style="padding:5px">Description</th>
                <th style="padding:5px">Coop ID</th>
                <th style="padding:5px">Student Name</th>
                <th style="padding:5px">Student Id</th>
              </tr>
              <tr v-for="reminder in reminders">
                <td style="padding:5px">{{ reminder.urgency }}</td>
                <td style="padding:5px">{{ reminder.subject }}</td>
                <td style="padding:5px">{{ reminder.description }}</td>
                <td style="padding:5px">{{ reminder.coop.coopId }}</td>
                <td
                  style="padding:5px"
                >{{ reminder.coop.student.firstName + " " + reminder.coop.student.lastName}}</td>
                <td style="padding:5px">{{ reminder.coop.student.id }}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
</style>