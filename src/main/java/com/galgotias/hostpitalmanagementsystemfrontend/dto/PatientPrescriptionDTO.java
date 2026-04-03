package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientPrescriptionDTO {

        private String medicineName;
        private String dose;
        private String physicianName;
        private Date appointmentDate;
        private String status;
        public PatientPrescriptionDTO(String medicineName, String dose, String physicianName, Date appointmentDate) {
                this.medicineName = medicineName;
                this.dose = dose;
                this.physicianName = physicianName;
                this.appointmentDate = appointmentDate;
        }

}
