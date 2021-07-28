import React, { useState } from 'react';
import './customodal.css'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

const CustomModal = (props) => {
    const {
      buttonLabel,
      btnColor,
      modalClassName,
      title,
      body,
      confirmBtn,
      toggleSignOption
    } = props;
  
    const [modal, setModal] = useState(false);
  
    const toggle = () => setModal(!modal);
  
    return (
      <div>
        <Button className="toggle-button text-center" color={btnColor} onClick={toggle}>{buttonLabel}</Button>
        <Modal isOpen={modal} backdrop={true} toggle={toggle} className={modalClassName + " modal-dialog-centered"}>
            <ModalHeader toggle={toggle}>{title}</ModalHeader>
            <ModalBody>
              {body}
            </ModalBody>
            <ModalFooter>
                {toggleSignOption ? 
                  <Button style={{ margin:"0px 105px 0px 0px" }} color="info" onClick={(e) => toggleSignOption(e)}>
                    {modalClassName === "cusmodal-signin" ? "You're new? Sign up now" : "Already in? Sign in now"}
                  </Button>
                  : null}
                {confirmBtn}
                <Button color="secondary" onClick={toggle}>Cancel</Button>
            </ModalFooter>
        </Modal>
      </div>
    );
  }
  
  export default CustomModal;