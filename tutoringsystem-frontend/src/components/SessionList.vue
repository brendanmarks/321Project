 <template>
  <div id="id">
    <div class="site-wrap">
      <div class="site-section" style="padding-top:10%">
        <div class="container" style="align:center">
          <h1 class="page-title">Sessions</h1>
          <div id="table">
            <table class="info-table" style="width: 100%; height: 100%;">
              <tr>
                <th style="padding:5px">ID</th>
                <th style="padding:5px">DATE</th>
                <th style="padding:5px">TIME</th>
                <th style="padding:5px">TUTOR NAME</th>
                <th style="padding:5px">Student Name</th>
              </tr>
              <tr v-for="session in sessions" :key="session">
                <td style="padding:5px">{{ session.id }}</td>
                <td style="padding:5px">{{ session.date }}</td>
                <td style="padding:5px">{{ session.time }}</td>
                <td style="padding:5px">{{ session.tutor.name }}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./javascript/filterSessions.js"></script>