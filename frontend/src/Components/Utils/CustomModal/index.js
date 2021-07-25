import React, { useState } from 'react';
import './customodal.css'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

const CustomModal = (props) => {
    const {
      buttonLabel,
      className,
      title,
      body,
      onConfirm,
      confirmButtonLabel
    } = props;
  
    const [modal, setModal] = useState(false);
  
    const toggle = () => setModal(!modal);
  
    return (
      <div>
        <Button color="info" onClick={toggle}>{buttonLabel}</Button>
        <Modal isOpen={modal} backdrop={true} toggle={toggle} className={className}>
            <ModalHeader toggle={toggle}>{title}</ModalHeader>
            <ModalBody>
              {body}
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={onConfirm}>{confirmButtonLabel}</Button>{' '}
                <Button color="secondary" onClick={toggle}>Cancel</Button>
            </ModalFooter>
        </Modal>
      </div>
    );
  }
  
  export default CustomModal;