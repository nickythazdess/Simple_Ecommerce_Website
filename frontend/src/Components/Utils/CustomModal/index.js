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
      confirmBtn
    } = props;
  
    const [modal, setModal] = useState(false);
  
    const toggle = () => setModal(!modal);
  
    return (
      <div>
        <Button color={btnColor} onClick={toggle}>{buttonLabel}</Button>
        <Modal isOpen={modal} backdrop={true} toggle={toggle} className={modalClassName}>
            <ModalHeader toggle={toggle}>{title}</ModalHeader>
            <ModalBody>
              {body}
            </ModalBody>
            <ModalFooter>
                {confirmBtn}
                <Button color="secondary" onClick={toggle}>Cancel</Button>
            </ModalFooter>
        </Modal>
      </div>
    );
  }
  
  export default CustomModal;