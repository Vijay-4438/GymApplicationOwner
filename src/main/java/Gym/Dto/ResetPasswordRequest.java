package Gym.Dto;


    public class ResetPasswordRequest {
        private String email;
        private String newPassword;
        private String otp;

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getNewPassword() {
            return newPassword;
        }
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
        public String getEmail1() {
            // TODO Auto-generated method stub
            return email;
        }
        public String getNewPassword1() {
            // TODO Auto-generated method stub
            return newPassword;
        }
}
